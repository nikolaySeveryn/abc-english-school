package nks.abc.domain.view.validation.client;

import java.util.HashMap;
import java.util.Map;

import javax.validation.metadata.ConstraintDescriptor;

import org.primefaces.validate.bean.ClientValidationConstraint;

import nks.abc.domain.view.validation.annotation.Unique;

public class UniqueClientValidator  implements ClientValidationConstraint{
	
	public static final String MESSAGE_METADATA = "data-p-email-msg";

	@Override
	public Map<String,Object> getMetadata(ConstraintDescriptor constraintDescriptor) {
		Map<String,Object> metadata = new HashMap<String, Object>();
        Map<String,Object> attrs = constraintDescriptor.getAttributes();
        Object message = attrs.get("message");    
        if(message != null) {
            metadata.put(MESSAGE_METADATA, message);
        }
         
        return metadata;
	}

	@Override
	public String getValidatorId() {
		return Unique.class.getSimpleName();
	}

}
