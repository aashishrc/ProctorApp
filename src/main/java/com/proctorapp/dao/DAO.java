package com.proctorapp.dao;

import com.proctorapp.model.Users;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

public class DAO {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null){
            try{
                StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .configure()
                        .build();
                MetadataSources metadataSources = new MetadataSources(serviceRegistry);
                metadataSources.addAnnotatedClass(Users.class);

                Metadata metadata = metadataSources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
