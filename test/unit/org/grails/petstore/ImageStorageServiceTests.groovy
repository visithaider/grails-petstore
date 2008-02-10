package org.grails.petstore

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import org.springframework.mock.web.MockMultipartFile


class ImageStorageServiceTests extends GroovyTestCase {

    def imageStorageService = new ImageStorageService()

    // TODO: this directory should be part of the classpath, but isn't
    //def res = new ClassPathResource("test.jpg")
    def res = new FileSystemResource("test/resources/test.jpg")

    def file = new MockMultipartFile("test.jpg", res.inputStream)

    protected void setUp() {
        imageStorageService.imageDir = new ClassPathResource("").file.absolutePath + "/images"
        imageStorageService.createDirectories()
    }

    void testStoreUploadedImage() {

        def path = imageStorageService.storeUploadedImage(file.bytes, "image/jpg")

        assert path != null
        assert path =~ /.*\.jpg$/

        try {
            imageStorageService.storeUploadedImage(file.bytes, "not/supported")
            fail "Should not be possible to store images with content type $contentType"
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    void testStoreProductImage() {
        imageStorageService.storeProductImage(this.file.name, this.file.bytes)

        assert new File(imageStorageService.productDir, file.name).exists()
    }

    void testStoreCategoryImage() {
        imageStorageService.storeCategoryImage(this.file.name, this.file.bytes)

        assert new File(imageStorageService.categoryDir, file.name).exists()

    }

    protected void tearDown() {
        imageStorageService.clearDirectories()
    }


}