package letsit_backend.model;

import lombok.Data;

import java.util.Properties;

@Data
public class KakaoProfile {
    public long id;
    //public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_Account;

    @Data
    public class Properties { //(1)
        public String profile_image; // 이미지 경로 필드1
    }

    @Data
    public class KakaoAccount {
        public String name;
        public String gender;
        public String age_range;

        //public Boolean profile_nickname_needs_agreement;
        public Boolean profile_image_needs_agreement;
        public Profile profile;
        //public Boolean has_email;
        //public Boolean email_needs_agreement;
        //public Boolean is_email_valid;
        //public Boolean is_email_verified;
        public String email;


        @Data
        public class Profile {
            public String profile_image_url; // 이미지 경로 필드2
            public Boolean is_default_image;
        }
    }


}
