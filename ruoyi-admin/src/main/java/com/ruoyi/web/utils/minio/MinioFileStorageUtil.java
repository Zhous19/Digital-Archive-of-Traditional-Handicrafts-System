package com.ruoyi.web.utils.minio;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * MinIO 文件存储工具类（仅无日期路径方法）
 */
@Component
@ConditionalOnBean(MinioClient.class)
public class MinioFileStorageUtil {

    private static final Logger log = LoggerFactory.getLogger(MinioFileStorageUtil.class);

    private static final String SEPARATOR = "/";
    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    private static final Map<String, String> CONTENT_TYPE_MAPPING = initContentTypeMapping();

    private static Map<String, String> initContentTypeMapping() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        mapping.put("doc", "application/msword");
        mapping.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        mapping.put("xls", "application/vnd.ms-excel");
        mapping.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        mapping.put("ppt", "application/vnd.ms-powerpoint");
        mapping.put("pdf", "application/pdf");
        mapping.put("jpg", "image/jpeg");
        mapping.put("jpeg", "image/jpeg");
        mapping.put("png", "image/png");
        mapping.put("html", "text/html");
        mapping.put("htm", "text/html");
        mapping.put("txt", "text/plain");
        mapping.put("csv", "text/csv");
        mapping.put("mp4", "video/mp4");
        mapping.put("webm", "video/webm");
        mapping.put("avi", "video/x-msvideo");
        mapping.put("rmvb", "application/vnd.rn-realmedia-vbr");
        return Collections.unmodifiableMap(mapping);
    }

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    public MinioFileStorageUtil(MinioClient minioClient, MinioProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
    }

    /**
     * 无日期的文件路径构建（path/filename）
     */
    public String buildFilePathWithoutDate(String path, String filename) {
        StringBuilder sb = new StringBuilder(50);
        if (path != null && !path.isEmpty()) {
            sb.append(path).append(SEPARATOR);
        }
        sb.append(filename);
        return sb.toString();
    }

    /**
     * 上传图片文件（无日期路径）
     */
    public String uploadImageFileWithoutDate(String prefix, String filename, InputStream inputStream) {
        return uploadImageFileWithoutDate(prefix, filename, inputStream, null);
    }

    /**
     * 上传图片文件（无日期路径，可选 contentType）
     */
    public String uploadImageFileWithoutDate(String prefix, String filename, InputStream inputStream,
            String contentType) {
        String filePath = buildFilePathWithoutDate(prefix, filename);
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType(resolveContentType(filename, contentType))
                    .bucket(minioProperties.getBucket())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(args);
            return buildFileUrl(minioProperties.getBucket(), filePath);
        } catch (Exception e) {
            log.error("Minio upload image error.", e);
            throw new RuntimeException("Minio 上传图片失败: " + e.getMessage());
        }
    }

    /**
     * 上传 MP4/视频文件（无日期路径）
     */
    public String uploadMp4FileWithoutDate(String prefix, String filename, InputStream inputStream) {
        return uploadMp4FileWithoutDate(prefix, filename, inputStream, null);
    }

    /**
     * 上传 MP4/视频文件（无日期路径，可选 contentType）
     */
    public String uploadMp4FileWithoutDate(String prefix, String filename, InputStream inputStream,
            String contentType) {
        String filePath = buildFilePathWithoutDate(prefix, filename);
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType(resolveContentType(filename, contentType))
                    .bucket(minioProperties.getBucket())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(args);
            return buildFileUrl(minioProperties.getBucket(), filePath);
        } catch (Exception e) {
            log.error("Minio upload video error.", e);
            throw new RuntimeException("Minio 上传视频失败: " + e.getMessage());
        }
    }

    /**
     * 上传文件（无日期路径，通用）
     */
    public String uploadFileWithoutDate(String prefix, String filename, InputStream inputStream) {
        return uploadFileWithoutDate(prefix, filename, inputStream, null);
    }

    /**
     * 上传文件（无日期路径，可选 contentType）
     */
    public String uploadFileWithoutDate(String prefix, String filename, InputStream inputStream,
            String contentType) {
        String filePath = buildFilePathWithoutDate(prefix, filename);
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType(resolveContentType(filename, contentType))
                    .bucket(minioProperties.getBucket())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(args);
            return buildFileUrl(minioProperties.getBucket(), filePath);
        } catch (Exception e) {
            log.error("Minio upload file error.", e);
            throw new RuntimeException("Minio 上传文件失败: " + e.getMessage());
        }
    }

    /**
     * 上传对象（无日期路径，自定义 bucket 与 objectName）
     *
     * @param bucket     桶名
     * @param objectName 对象名（含路径），如 work/1/image/xxx.png
     * @param filename   用于推断 contentType 的文件名（通常与 objectName 的末段一致）
     */
    public String uploadObjectWithoutDate(String bucket, String objectName, String filename, InputStream inputStream,
            String contentType) {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .contentType(resolveContentType(filename, contentType))
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(args);
            return buildFileUrl(bucket, objectName);
        } catch (Exception e) {
            log.error("Minio upload object error. bucket:{} objectName:{}", bucket, objectName, e);
            throw new RuntimeException("Minio 上传文件失败: " + e.getMessage());
        }
    }

    private String resolveContentType(String filename, String customContentType) {
        if (customContentType != null && !customContentType.trim().isEmpty()) {
            return customContentType.trim();
        }
        if (filename == null || filename.trim().isEmpty()) {
            return DEFAULT_CONTENT_TYPE;
        }
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == filename.length() - 1) {
            return DEFAULT_CONTENT_TYPE;
        }
        String extension = filename.substring(dotIndex + 1).toLowerCase(Locale.ROOT);
        return CONTENT_TYPE_MAPPING.getOrDefault(extension, DEFAULT_CONTENT_TYPE);
    }

    private String buildFileUrl(String bucket, String objectName) {
        return minioProperties.getEndpoint() + SEPARATOR + bucket + SEPARATOR + objectName;
    }

    /**
     * 删除对象（bucket + objectName）
     */
    public void deleteObject(String bucket, String objectName) {
        if (bucket == null || bucket.trim().isEmpty() || objectName == null || objectName.trim().isEmpty()) {
            return;
        }
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(objectName).build());
        } catch (Exception e) {
            log.warn("Minio delete object failed. bucket:{} objectName:{}", bucket, objectName, e);
            throw new RuntimeException("Minio 删除文件失败: " + e.getMessage());
        }
    }

    /**
     * 根据 URL 删除对象（仅支持当前 endpoint + bucket 的直链格式）
     * <p>
     * 期望格式：{endpoint}/{bucket}/{objectName}
     */
    public void deleteObjectByUrl(String url) {
        parseObjectFromUrl(url).ifPresent(parsed -> deleteObject(parsed.bucket, parsed.objectName));
    }

    /**
     * 尝试从 URL 中解析出 bucket 与 objectName。
     */
    private Optional<ParsedObject> parseObjectFromUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return Optional.empty();
        }
        String endpoint = minioProperties.getEndpoint();
        String bucket = minioProperties.getBucket();
        if (endpoint == null || endpoint.trim().isEmpty() || bucket == null || bucket.trim().isEmpty()) {
            return Optional.empty();
        }
        try {
            String normalizedEndpoint = trimTrailingSlash(endpoint.trim());
            URI urlUri = URI.create(url.trim());
            URI endpointUri = URI.create(normalizedEndpoint);

            if (!equalsIgnoreCaseSafe(urlUri.getScheme(), endpointUri.getScheme())
                    || !equalsIgnoreCaseSafe(urlUri.getHost(), endpointUri.getHost())
                    || getPortOrDefault(urlUri) != getPortOrDefault(endpointUri)) {
                return Optional.empty();
            }

            String path = urlUri.getPath(); // starts with /
            if (path == null || path.isEmpty()) {
                return Optional.empty();
            }
            String expectedPrefix = SEPARATOR + bucket + SEPARATOR;
            if (!path.startsWith(expectedPrefix)) {
                return Optional.empty();
            }
            String objectName = path.substring(expectedPrefix.length());
            if (objectName.trim().isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(new ParsedObject(bucket, objectName));
        } catch (Exception e) {
            log.debug("Parse minio url failed, skip delete. url:{}", url, e);
            return Optional.empty();
        }
    }

    private String trimTrailingSlash(String s) {
        if (s == null)
            return null;
        while (s.endsWith(SEPARATOR)) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    private boolean equalsIgnoreCaseSafe(String a, String b) {
        if (a == null && b == null)
            return true;
        if (a == null || b == null)
            return false;
        return a.equalsIgnoreCase(b);
    }

    private int getPortOrDefault(URI uri) {
        int port = uri.getPort();
        if (port >= 0)
            return port;
        String scheme = uri.getScheme();
        if ("https".equalsIgnoreCase(scheme))
            return 443;
        if ("http".equalsIgnoreCase(scheme))
            return 80;
        return -1;
    }

    private static class ParsedObject {
        final String bucket;
        final String objectName;

        ParsedObject(String bucket, String objectName) {
            this.bucket = bucket;
            this.objectName = objectName;
        }
    }
}
