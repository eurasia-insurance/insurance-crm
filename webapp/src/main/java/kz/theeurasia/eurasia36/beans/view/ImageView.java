package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.lapsa.image.ImageContent;
import com.lapsa.image.ImageProcessingFactory;
import com.lapsa.image.ImageProcessingFactory.ProportionalResizeMode;
import com.lapsa.image.ImageProcessingFactoryProvider;
import com.lapsa.insurance.domain.UploadedImage;

@Named("imageView")
@RequestScoped
public class ImageView implements Serializable {
    private static final long serialVersionUID = -6873112909784840108L;

    private UploadedImage image;

    private ImageProcessingFactory prov;

    @PostConstruct
    public void init() {
	prov = ImageProcessingFactoryProvider.getInstance();
    }

    public UploadedImage getImage() {
	return image;
    }

    public void setImage(UploadedImage image) {
	this.image = image;
	this.resized = image == null ? null
		: prov.resizeImageContent(image.getOriginal(), ProportionalResizeMode.BY_HEIGHT, 800);
    }

    private ImageContent resized;

    public ImageContent getResized() {
	return resized;
    }

}
