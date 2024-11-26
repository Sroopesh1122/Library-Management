package com.library.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryConfiguration {
	public static Cloudinary getCloudinaryInstance() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "druhdw3tu",
                "api_key", "351726684652899",
                "api_secret", "5bUqj8bsSaGpLEhMlupDjxIco3w"));
    }
}
