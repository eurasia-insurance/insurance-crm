package tech.lapsa.insurance.crm.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import org.omnifaces.converter.SelectItemsConverter;

import com.lapsa.insurance.domain.crm.User;

import tech.lapsa.java.commons.function.MyObjects;
import tech.lapsa.java.commons.function.MyOptionals;
import tech.lapsa.java.commons.function.MyPredicates;

@FacesConverter(forClass = User.class)
public class UserSelectItemConverter extends SelectItemsConverter {

    @Override
    public String getAsString(FacesContext context, UIComponent component,
	    Object value) {

	return MyOptionals.of(value) //
		.filter(MyPredicates.isA(User.class))
		.map(MyObjects.cast(User.class)) //
		.map(User::getId) //
		.map(String::valueOf) //
		.orElse(null);
    }
}
