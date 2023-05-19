package entity;

import entity.Customer;
import entity.Shoe;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-05-19T13:26:08")
@StaticMetamodel(Purchase.class)
public class Purchase_ { 

    public static volatile SingularAttribute<Purchase, Date> purchaseDate;
    public static volatile SingularAttribute<Purchase, Long> id;
    public static volatile SingularAttribute<Purchase, Shoe> shoe;
    public static volatile SingularAttribute<Purchase, Integer> Qtty;
    public static volatile SingularAttribute<Purchase, Customer> customer;

}