package helmyfadlail.technicaltestbva.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        final Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dkysldily");
        config.put("api_key", "365282686288269");
        config.put("api_secret", "WkrlsebOMHYAd2OQBxTVuFjJyuA");
        return new Cloudinary(config);
    }
}
