/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Purchase;
import entity.Shoe;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author pupil
 */
@Stateless
public class PurchaseFacade extends AbstractFacade<Purchase> {
    
    @EJB private ShoeFacade shoeFacade;

    @PersistenceContext(unitName = "WebShoeShopPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PurchaseFacade() {
        super(Purchase.class);
    }
    
    public Map<Shoe, Integer> getPurchaseInPeriod(String yearStr, String month, String day) {
       if(yearStr== null || yearStr.isEmpty()){
            return new HashMap<>();
        }
        List<Purchase> listPurchases = null;
        //Если выбран только год
        if((month == null || month.isEmpty()) && (day == null || day.isEmpty())){
            LocalDateTime date1 = LocalDateTime.of(Integer.parseInt(yearStr),1, 1, 0, 0, 0); 
            LocalDateTime date2 = date1.plusYears(1);
            Date beginYear = Date.from(date1.atZone(ZoneId.systemDefault()).toInstant());
            Date nextYear = Date.from(date2.atZone(ZoneId.systemDefault()).toInstant());
            listPurchases = em.createQuery("SELECT p FROM Purchase p WHERE p.purchaseDate > :beginYear AND p.purchaseDate< :nextYear")
                .setParameter("beginYear", beginYear)
                .setParameter("nextYear", nextYear)
                .getResultList();
        //Если выбран год и месяц
        }else if((month != null || !month.isEmpty()) && (day == null || day.isEmpty())){
            LocalDateTime date1 = LocalDateTime.of(Integer.parseInt(yearStr),Integer.parseInt(month), 1, 0, 0, 0); 
            LocalDateTime date2 = date1.plusMonths(1);
            Date beginMonth = Date.from(date1.atZone(ZoneId.systemDefault()).toInstant());
            Date nextMonth = Date.from(date2.atZone(ZoneId.systemDefault()).toInstant());
            listPurchases = em.createQuery("SELECT p FROM Purchase p WHERE p.purchaseDate > :beginMonth AND p.purchaseDate < :nextMonth")
                .setParameter("beginMonth", beginMonth)
                .setParameter("nextMonth", nextMonth)
                .getResultList();
        }else{//Если выбран год, месяц и день
            LocalDateTime date1 = LocalDateTime.of(Integer.parseInt(yearStr),Integer.parseInt(month), Integer.parseInt(day), 0, 0, 0); 
            LocalDateTime date2 = date1.plusDays(1);
            Date beginDay = Date.from(date1.atZone(ZoneId.systemDefault()).toInstant());
            Date nextDay = Date.from(date2.atZone(ZoneId.systemDefault()).toInstant());
            listPurchases = em.createQuery("SELECT p FROM Purchase p WHERE p.purchaseDate > :beginDay AND p.purchaseDate< :nextDay")
                .setParameter("beginDay", beginDay)
                .setParameter("nextDay", nextDay)
                .getResultList();
        }
        //Map для хранения сопоставления книга -> сколько раз выдана
        Map<Shoe, Integer>mapShoeRange = new HashMap<>();
        List<Shoe> shoe = shoeFacade.findAll();
        for (int i = 0; i< shoe.size(); i++) { //перебираем все книги
            mapShoeRange.put(shoe.get(i), 0);
            for (Purchase purchase : listPurchases) {
                if(purchase.getShoe().equals(shoe.get(i))){
                    Integer n = mapShoeRange.get(shoe.get(i));
                    n++;//к n добавляем 1, если книга есть в истории
                    mapShoeRange.put(shoe.get(i), n);//обновляем значение n для книги
                }
            }
        }
        return mapShoeRange; // возвращаем карту Книга->сколько раз выдана за указанный период
    }
    
}


