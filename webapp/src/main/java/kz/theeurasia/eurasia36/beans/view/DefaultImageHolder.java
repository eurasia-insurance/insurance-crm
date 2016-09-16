package kz.theeurasia.eurasia36.beans.view;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import com.lapsa.donkeyfaces.model.Image;

import kz.theeurasia.eurasia36.beans.api.ImageHolder;

@Named("image")
@RequestScoped
public class DefaultImageHolder extends DefaultWritableValueHolder<Image> implements Serializable, ImageHolder {
    private static final long serialVersionUID = -6873112909784840108L;

    @Override
    public void reset() {
	value = null;
    }
}
