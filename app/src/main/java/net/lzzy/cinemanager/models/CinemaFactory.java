package net.lzzy.cinemanager.models;

import net.lzzy.cinemanager.constants.DbConstants;
import net.lzzy.cinemanager.utils.AppUtils;
import net.lzzy.sqllib.SqlRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzzy_gxy on 2019/3/11.
 * Description:
 */
public class CinemaFactory {
    private static final CinemaFactory OUR_INSTANCE = new CinemaFactory();
    private SqlRepository<Cinema> repository;

    public static CinemaFactory getInstance() {
        return OUR_INSTANCE;
    }

    private CinemaFactory() {
        repository = new SqlRepository<>(AppUtils.getContext(), Cinema.class, DbConstants.packager);
    }

    public List<Cinema> get() {
        return repository.get();
    }

    public Cinema getById(String id) {
        return repository.getById(id);
    }


    public List<Cinema> searchCinemas(String kw){
        List<Cinema> result = new ArrayList<>();
        List<Cinema> all = get();
        for(Cinema cinema:all){
            if(cinema.toString().contains(kw)){
                result.add(cinema);
            }
        }
        return result;
    }

    private boolean isCinemaExists(Cinema cinema) {
        return searchCinemas(cinema.toString()).size()>0;
    }

    public boolean addCinema(Cinema cinema){
        if(!isCinemaExists(cinema)){
            repository.insert(cinema);
            return true;
        }
        return false;
    }

    public boolean deleteCinema(Cinema cinema){
        repository.delete(cinema);
        return true;
    }
}
