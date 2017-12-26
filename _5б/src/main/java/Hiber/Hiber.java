package Hiber;

import com.my.AppleEntity;
import com.my.PriceEntity;
import com.my.WarehouseEntity;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import sun.font.TrueTypeFont;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.Scanner;

public class Hiber {
    private static int state;
    private static boolean bool = true;
    public static void main(final String[] args) throws Exception {
        SessionFactory ourSessionFactory = null;
        Session session = null;
        try {
            ourSessionFactory = new Configuration().configure().buildSessionFactory();
            session = ourSessionFactory.openSession();

            while (bool){
                Scanner input = new Scanner(System.in);
                System.out.println("Input 1 for read data from all tables");
                System.out.println("Input 2 for readPriceFilter");
                System.out.println("Input 3 for read Apple in Warehouse Price");
                System.out.println("Input 4 for read Price");
                System.out.println("Input 5 for insert Price");
                System.out.println("Input 6 for delete Price");
                System.out.println("Input 7 for update Price");
                System.out.println("Input 8 for insert Warehouse");
                System.out.println("Input 9 add apple to warehouse");
                System.out.println("Input Anything for exit");
                state = input.nextInt();

                switch (state) {
                    case 1:
                        readTables(session);
                        break;
                    case 2:
                        readPriceFilter(session);
                        break;
                    case 3:
                        readAppleOfWarehouse(session);
                        break;
                    case 4:
                        readPrice(session);
                        break;
                    case 5:
                        insertPrice(session);
                        break;
                    case 6:
                        deletePrice(session);
                        break;
                    case 7:
                        updatePrice(session);
                        break;
                    case 8:
                        insertWarehouse(session);
                        break;
                    case 9:
                        addAppleToWarehouse(session);
                        break;
                    default:
                        bool = false;
                        break;
                }
            }




        } catch (Exception e){
            System.out.println(e);
            System.out.println("Exception");
        } finally{
            if(session != null) {
                session.close();
            }
            if(ourSessionFactory != null) {
                ourSessionFactory.close();
            }
        }

    }
    private static void readTables(Session session){

        //Read Warehouse
        Query query = session.createQuery("from " + "WarehouseEntity");
        System.out.format("\nTable Warehouse --------------------\n");
        System.out.format("%3s %-12s %-12s %-10s %s\n", "ID", "WarehouseName", "Street", "City", "Email");
        for (Object obj : query.list()) {
            WarehouseEntity warehouse = (WarehouseEntity) obj;
            System.out.format("%3d %-12s %-12s %-10s %s\n", warehouse.getIdWarehouse(),
                    warehouse.getWarehouseName(), warehouse.getStreet(), warehouse.getCity(), warehouse.getEmail());
        }

        //Read Apple
        query = session.createQuery("from " + "AppleEntity");
        System.out.format("\nTable Apple --------------------\n");
        System.out.format("%3s %-18s %-18s %3s\n", "ID", "AppleName", "Amount", "ID_Price");
        for (Object obj : query.list()) {
            AppleEntity apple = (AppleEntity) obj;
            System.out.format("%3d %-18s %-18s %3d\n", apple.getIdApple(), apple.getAppleName(), apple.getAmount(), apple.getPriceByPrice().getIdPrice());
        }

        //region Read Price
        query = session.createQuery("from " + "PriceEntity");
        System.out.format("\nTable Price --------------------\n");
        System.out.format("%3s %-18s\n", "ID", "Price");
        for (Object obj : query.list()) {
            PriceEntity price = (PriceEntity) obj;
            System.out.format("%3s %-18s\n", price.getIdPrice(), price.getPrice());
        }
    }

    private static void readPriceFilter(Session session){

        Scanner input = new Scanner(System.in);
        System.out.println("Input Price ID value for Apple: ");
        int price_in = input.nextInt();

        PriceEntity priceEntity = (PriceEntity) session.load( PriceEntity.class, price_in);
        if(priceEntity!=null){
            System.out.format("\n%d: %s\n", price_in, "AppleName");
            for (AppleEntity obj : priceEntity.getAppleByPrice())
                System.out.format("    %s\n", obj.getAppleName());
        }
        else System.out.println("invalid Price ID value");
    }

    private static void readAppleOfWarehouse(Session session){
        Query query = session.createQuery("from " + "WarehouseEntity");
        System.out.format("\nTable Warehouse --------------------\n");
        System.out.format("%3s %-12s \n","ID", "WarehouseName");
        for (Object obj : query.list()) {
            WarehouseEntity warehouse = (WarehouseEntity) obj;
            System.out.format("%3s %-12s\n", warehouse.getIdWarehouse(), warehouse.getWarehouseName());
            for (AppleEntity appley : warehouse.getApples()) {
                System.out.format("\t\t%s \n", appley.getAppleName());
            }
        }
    }

    private static void readPrice(Session session){
        Query query = session.createQuery("from " + "PriceEntity");
        System.out.format("\nTable Price --------------------\n");
        System.out.format("%3s %s \n","ID", "Price");
        for (Object obj : query.list()) {
            PriceEntity price = (PriceEntity) obj;
            System.out.format("%3d %s\n", price.getIdPrice(), price.getPrice());
        }
    }

    private static void insertPrice(Session session){
        Scanner input = new Scanner(System.in);
        System.out.println("Input a new Price: ");
        String newPrice = input.next();

        session.beginTransaction();
        PriceEntity priceEntity = new PriceEntity(newPrice);
        session.save(priceEntity);
        session.getTransaction().commit();

        System.out.println("end insert price");
    }

    private static void insertWarehouse(Session session){
        Scanner input = new Scanner(System.in);
        System.out.println("Input new Warehouse Name: ");
        String warehouse_new = input.next();
        System.out.println("Input the City for Warehouse: ");
        String city = input.next();
        System.out.println("Input new Street for Warehouse: ");
        String street = input.next();
        System.out.println("Input new Person Email: ");
        String email = input.next();


        session.beginTransaction();
        WarehouseEntity warehouseEntity = new WarehouseEntity(warehouse_new, city, street, email);
        session.save(warehouseEntity);
        session.getTransaction().commit();
        System.out.println("end insert warehouse");
    }

    private static void updatePrice(Session session){
        Scanner input = new Scanner(System.in);
        System.out.println("\nInput old Price value: ");
        int oldPrice = input.nextInt();
        System.out.println("Input new Price value: ");
        int newPrice = input.nextInt();

        PriceEntity priceEntity = (PriceEntity) session.load( PriceEntity.class, oldPrice);
        if(priceEntity!=null){
            session.beginTransaction();
            Query query = session.createQuery("UPDATE PriceEntity SET Price=:code1  WHERE Price=:code2");
            query.setParameter("code1", newPrice);
            query.setParameter("code2", oldPrice);
            int result = query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("end update Price: "+ result);
        }
        else System.out.println("No such Price");
    }

    private static void deletePrice(Session session){
        Scanner input = new Scanner(System.in);
        System.out.println("\n Input Price value for DELETE: ");
        int price = input.nextInt();

        PriceEntity priceEntity = (PriceEntity) session.load( PriceEntity.class, price);
        if(priceEntity!=null){
            session.beginTransaction();
            Query query = session.createQuery("DELETE PriceEntity WHERE Price = :code1");
            query.setParameter("code1", price);
            int result = query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("end delete Price: "+ result);
        }
        else System.out.println("No such price For DELETE");
    }

    private static void addAppleToWarehouse(Session session){
        System.out.println("Add an apple to warehouse--------------");
        Scanner input = new Scanner(System.in);
        System.out.println("Choose Warehouse NAME:");
        String warname_in = input.next();
        System.out.println("Choose Name of Apple:");
        String apple_in = input.next();

        Query query = session.createQuery("from " + "WarehouseEntity WHERE WarehouseName = :code");
        query.setParameter("code", warname_in);

        if(!query.list().isEmpty()){
            WarehouseEntity warehouseEntity = (WarehouseEntity)query.list().get(0);
            query = session.createQuery("from " + "AppleEntity where AppleName = :code");
            query.setParameter("code", apple_in);
            if(!query.list().isEmpty()){
                AppleEntity appleEntity = (AppleEntity)query.list().get(0);
                session.beginTransaction();
                warehouseEntity.addAppleEntity(appleEntity);
                session.save(warehouseEntity);
                session.getTransaction().commit();
                System.out.println("end insert apple to warehouse");
            }
            else {System.out.println("No such apple");}
        }
        else {System.out.println("No such warehouse");}

    }

}
