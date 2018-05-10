package com.adinstar.pangyo.model.authorization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KProperties {
    private String nickname;
    private String thumbnailImage;
    private String profileImage;
    private String age;
    private String gender;

    @JsonSetter("thumbnail_image")
    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    @JsonSetter("profile_image")
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @JsonSetter("custom_field1")
    public void setAge(String age) {
        this.age = age;
    }

    @JsonSetter("custom_field2")
    public void setGender(String gender) {
        this.gender = gender;
    }
}
