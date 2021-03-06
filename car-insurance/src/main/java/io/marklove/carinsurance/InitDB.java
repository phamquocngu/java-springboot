package io.marklove.carinsurance;

import io.marklove.carinsurance.constant.Constants;
import io.marklove.carinsurance.constant.UserStatus;
import io.marklove.carinsurance.entity.Admin;
import io.marklove.carinsurance.entity.CompanyGroup;
import io.marklove.carinsurance.entity.car.Category;
import io.marklove.carinsurance.entity.embeddable.FileInfo;
import io.marklove.carinsurance.repository.CategoryRepository;
import io.marklove.carinsurance.repository.CompanyGroupRepository;
import io.marklove.carinsurance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

@Component
@Profile({"prd"})
@Log4j2
@RequiredArgsConstructor
public class InitDB implements CommandLineRunner {
    private static String[] ICON_CATEGORY = {"category/ppf.png", "category/polish.png",
            "category/wrapping.png", "category/black_box.png", "category/new_car_package.png",
            "category/tinting.png", "category/glass_film.png", "category/windshield.png"};

    private static final String ADMIN_ID = "admin";

    private final CategoryRepository categoryRepository;
    private final CompanyGroupRepository companyGroupRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("================== BEGIN INIT DATA ====================");
        initCategory();
        initGroup();
        initAdminAccount();
        log.info("================== END INIT DATA ====================");
    }

    private void initAdminAccount() {
        if(!userRepository.findByMemberId(ADMIN_ID).isPresent()) {
            final var encPass = passwordEncoder.encode("Aa@12345");
            final var encPwd = Base64.getEncoder().encodeToString("Aa@12345".getBytes(StandardCharsets.UTF_8));
            //Admin member
            Admin admin = Admin.builder().memberId(ADMIN_ID)
                    .password(encPass)
                    .encodePwd(encPwd)
                    .status(UserStatus.ACTIVATED)
                    .lastLoggedIn(LocalDateTime.now())
                    .build();
            userRepository.save(admin);
        }
    }

    private void initCategory() throws Exception {
        //Init category icon
//        for(String path : ICON_CATEGORY) {
//            Resource resource = new ClassPathResource("init-data/" + path);
//            if(resource != null && resource.exists()) {
//                byte[] bytes = IOUtils.toByteArray(resource.getInputStream());
//                storageService.upload(bytes, path, true);
//            }
//        }

        if(categoryRepository.findAll().isEmpty()) {
            categoryRepository.save(Category.builder().orderCategory(1).title("PPF")
                    .icon(new FileInfo("ppf.png", "category/ppf.png")).build());
            categoryRepository.save(Category.builder().orderCategory(2).title("??????")
                    .icon(new FileInfo("polish.png", "category/polish.png")).build());
            categoryRepository.save(Category.builder().orderCategory(3).title("??????")
                    .icon(new FileInfo("wrapping.png", "category/wrapping.png")).build());
            categoryRepository.save(Category.builder().orderCategory(4).title("????????????")
                    .icon(new FileInfo("black_box.png", "category/black_box.png")).build());
            categoryRepository.save(Category.builder().orderCategory(5).title("???????????????")
                    .icon(new FileInfo("new_car_package.png", "category/new_car_package.png")).build());
            categoryRepository.save(Category.builder().orderCategory(6).title("??????")
                    .icon(new FileInfo("tinting.png", "category/tinting.png")).build());
            categoryRepository.save(Category.builder().orderCategory(7).title("?????????")
                    .icon(new FileInfo("glass_film.png", "category/glass_film.png")).build());
            categoryRepository.save(Category.builder().orderCategory(8).title("????????????")
                    .icon(new FileInfo("windshield.png", "category/windshield.png")).build());
        }
    }

    private void initGroup() {
        if(!companyGroupRepository.findByName(Constants.GENERAL_GROUP).isPresent()) {
            CompanyGroup companyGroup = new CompanyGroup(Constants.GENERAL_GROUP);
            companyGroupRepository.save(companyGroup);
        }
    }
}
