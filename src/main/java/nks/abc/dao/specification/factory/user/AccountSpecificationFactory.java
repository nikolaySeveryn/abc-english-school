package nks.abc.dao.specification.factory.user;

import nks.abc.dao.specification.chunks.EqualSpecification;
import nks.abc.dao.specification.chunks.Specification;
import nks.abc.dao.specification.chunks.factory.AliasChunksFactory;
import nks.abc.dao.specification.chunks.factory.SpecificationChunksFactory;
import nks.abc.dao.specification.factory.CommonSpecificationFactory;
import nks.abc.domain.planing.impl.BookImpl;
import nks.abc.domain.user.impl.AccountImpl;

public class AccountSpecificationFactory extends CommonSpecificationFactory{
	
	private static final String ACTIVE_FIELD = "active";

	public static AccountSpecificationFactory buildFactory(){
		return new AccountSpecificationFactory();
	}
	
	public AccountSpecificationFactory() {
		super("id");
	}

	@Override
	protected SpecificationChunksFactory getChunksFactory() {
		return new AliasChunksFactory("account", AccountImpl.class);
	}
	
	public Specification active(){
		return active(true);
	}
	
	public Specification active(Boolean active){
		EqualSpecification specification = getChunksFactory().createEqualsSpecification();
		specification.setCriteria(ACTIVE_FIELD, active);
		return specification;
	}
	
	public Specification byEmail(String email) {
		EqualSpecification specification = getChunksFactory().createEqualsSpecification();
		specification.setCriteria("email", email);
		return specification;
	}
}
