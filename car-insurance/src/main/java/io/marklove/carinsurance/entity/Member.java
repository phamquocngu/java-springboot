package io.marklove.carinsurance.entity;

import io.marklove.carinsurance.constant.Role;
import io.marklove.carinsurance.constant.enums.EActivateStatus;
import io.marklove.carinsurance.constant.enums.EGender;
import io.marklove.carinsurance.constant.enums.ERegistrationStatus;
import io.marklove.carinsurance.constant.enums.ESNSType;
import io.marklove.carinsurance.entity.car.CarType;
import io.marklove.carinsurance.entity.embeddable.NoticeSetting;
import io.marklove.carinsurance.entity.transaction.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@DiscriminatorValue(value = Role.Constant.MEMBER)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Member extends User {

    private String name;

    private String phone;

    @Column(columnDefinition = "DATE")
    private LocalDate birthday;

    @Column(columnDefinition = "tinyint")
    private EGender gender;

    @Column(columnDefinition = "tinyint")
    private ESNSType sns;

    // TODO SNS link

    // TODO member status: normal(default), inactivity(inactive after 365 days)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="group_id", foreignKey = @ForeignKey(name = "FK_user_group"))
    private CompanyGroup group;

    private boolean companyMember;

    private long point;

    // Consent to receive advertisements
    private boolean isAdNotified;

    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_type_id", foreignKey = @ForeignKey(name = "FK_user_car_type"))
    private CarType carType;

    private String carNumber;

    @Embedded
    private NoticeSetting noticeSetting = new NoticeSetting();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requestUser")
    private Set<Company> companies;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private SNSInfo snsInfo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "writer")
    private Set<ConstructionReview> reviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "requester")
    private Set<Transaction> transactions;

    public String getCarInfo() {
        if(carType != null) return  this.carType.getCarInfo();

        return "";
    }

    public long requestQuotes() {
        if(transactions != null) return transactions.size();

        return 0;
    }

    public long totalReviews() {
        if(reviews != null) return reviews.size();

        return 0;
    }

    public boolean isCompanyUser() {
        if(companies == null || companies.isEmpty()) {
            return false;
        }

        if(companies.stream().anyMatch(c -> c.getActivate() == EActivateStatus.ACTIVATE &&
                c.getProcessingStatus() == ERegistrationStatus.APPROVE)) {
            return true;
        } else {
            return false;
        }
    }

    public Member(long id) {
        super(id);
    }

    public Company getCompany() {
        if(companies == null || companies.isEmpty()) {
            return null;
        }

        var rs = companies.stream()
                .filter(c -> c.getActivate() == EActivateStatus.ACTIVATE)
                .findFirst();
        if(rs.isPresent()) {
            return rs.get();
        } else {
            return null;
        }
    }
}
