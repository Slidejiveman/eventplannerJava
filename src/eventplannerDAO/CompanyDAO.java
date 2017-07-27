package eventplannerDAO;

import java.util.List;

import javax.persistence.TypedQuery;

import eventplannerPD.Company;

public class CompanyDAO {
	public static void saveCompany(Company company) {
	      EM.getEntityManager().persist(company);
	    }
	    public static void addCompany(Company company) {
	      EM.getEntityManager().persist(company);
	    }

	    public static List<Company> listCompany()
	    {
	      TypedQuery<Company> query = EM.getEntityManager().createQuery("SELECT company FROM company company", Company.class);
	      return query.getResultList();
	    }
}
