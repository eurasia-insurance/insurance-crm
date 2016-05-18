package kz.theeurasia.eurasia36.application;

import java.awt.Color;

import com.lapsa.image.ImageProcessingFactory.FixedResizeMode;
import com.lapsa.image.ImageProcessingFactory.ProportionalResizeMode;
import com.lapsa.image.ImageProcessingFactory.ResizeMode;

public interface ParameterConstants {
    public static final String[] ACCEPT_IMAGE_CONTENT_TYPES = { "image/jpeg", "image/jpg", "image/gif", "image/png" };
    public static final long MAXIMUM_IMAGE_UPLOAD_FILE_SIZE = 1024 * 1024 * 2; // 2MB

    public static final ResizeMode IMAGE_THUMBNAIL_RESIZE_MODE = ResizeMode.FIXED_SIZE;

    public static final FixedResizeMode IMAGE_THUMBNAIL_FIXED_RESIZE_MODE = FixedResizeMode.FIT;
    public static final int IMAGE_THUMBNAIL_FIXED_WIDTH = 115;
    public static final int IMAGE_THUMBNAIL_FIXED_HEIGHT = 115;
    public static final Color IMAGE_THUMBNAIL_FIXED_BACKGROUND_COLOR = Color.WHITE;

    public static final ProportionalResizeMode IMAGE_THUMBNAIL_PROPORTIONAL_RESIZE_MODE = ProportionalResizeMode.BY_HEIGHT;
    public static final int IMAGE_THUMBNAIL_PROPORTIONAL_RESIZE_TO = 100;
}
