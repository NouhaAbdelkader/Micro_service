package com.example.event.Services;


import com.example.event.entities.Event;
import com.example.event.entities.Rsvp;
import com.example.event.repositorys.EventRepo;
import com.example.event.repositorys.RsvpRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class EventServ implements IService<Event>{

    private final EventRepo repo;
    private final RsvpRepo rsvprepo;
    @Override
    public Event Create(Event event) {
        return repo.save(event);
    }

    @Override
    public Event Update(Event event) {
        return repo.save(event);
    }

    @Override
    public Event Retrieve(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Event> Retrieve() {
        return repo.findAll();
    }

    @Override
    public void Delete(Long id) {
        repo.deleteById(id);
    }

    public Event GetEstimation(Event event){


        System.out.println(event);



        List<Rsvp> list = rsvprepo.findAll();
        System.out.println("list : "+list);
        // Considering the Rsvp
        float somme =0;
        for(Rsvp r : list){
            if(r.getStatus().equals("Interested")){
                somme+=1;
            }else if(r.getStatus().equals("Maybe")){
                somme+= 0.5F;
            }
            System.out.println(r.getStatus());
        }
        System.out.println("Somme : "+somme);
        //Considering the Category of the event
        List<Rsvp> listall = rsvprepo.findAll();
        int countCategory = 0;
        for(Rsvp r : listall){
            if(r.getEvent().getCategory().equals(event.getCategory())|| r.getEvent().getId().equals(event.getId())){
                countCategory+=1;
            }
        }

        float percCat = ((float) countCategory /listall.size())*10;
        System.out.println("percCat : "+percCat);
        float estimation = ((somme/list.size())*100)+percCat;
        // Consedering the event price
        double pricemoy = 0;
        List<Event> events = repo.findAll();
        for(Event r : events){
            pricemoy= pricemoy +r.getPrice();
        }
        pricemoy = pricemoy / listall.size();
        if(event.getPrice() > pricemoy){
            float added = 0;
            added = (float) (event.getPrice()-pricemoy);
            estimation -=added;
        }else{
            float added = 0;
            added = (float) (pricemoy-event.getPrice());
            estimation +=added;
        }
        System.out.println("Estimation : "+ estimation);
        event.setEstimation(estimation);
        repo.save(event);
        return event;
    }

    public void GetAllEstimation(){

        List<Event> events = repo.findAll();

        for(Event event : events){

            //Event event = repo.findByName(name);
            System.out.println(event);



            List<Rsvp> list = rsvprepo.findAll();
            System.out.println("list : "+list);
            // Considering the Rsvp
            float somme =0;
            for(Rsvp r : list){
                if(r.getStatus().equals("Interested")){
                    somme+=1;
                }else if(r.getStatus().equals("Maybe")){
                    somme+= 0.5F;
                }
                System.out.println(r.getStatus());
            }
            System.out.println("Somme : "+somme);
            //Considering the Category of the event
            List<Rsvp> listall = rsvprepo.findAll();
            int countCategory = 0;
            for(Rsvp r : listall){
                if(r.getEvent().getCategory().equals(event.getCategory())|| r.getEvent().getId().equals(event.getId())){
                    countCategory+=1;
                }
            }

            float percCat = ((float) countCategory /listall.size())*10;
            System.out.println("percCat : "+percCat);
            float estimation = ((somme/list.size())*100)+percCat;
            // Consedering the event price
            double pricemoy = 0;
            //List<Event> events = repo.findAll();
            for(Event r : events){
                pricemoy= pricemoy +r.getPrice();
            }
            pricemoy = pricemoy / listall.size();
            if(event.getPrice() > pricemoy){
                float added = 0;
                added = (float) (event.getPrice()-pricemoy);
                estimation -=added;
            }else{
                float added = 0;
                added = (float) (pricemoy-event.getPrice());
                estimation +=added;
            }
            System.out.println("Estimation : "+ estimation);
            event.setEstimation(estimation);
            repo.save(event);

        }
    }
}
