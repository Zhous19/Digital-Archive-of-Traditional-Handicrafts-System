package com.ruoyi.web.utils.minio;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MinIO 配置属性
 */
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    /** 服务地址，如 http://127.0.0.1:9000 */
    private String endpoint;
    /** 桶名称 */
    private String bucket;
    /** 访问密钥 */
    private String accessKey;
    /** 秘密密钥 */
    private String secretKey;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
