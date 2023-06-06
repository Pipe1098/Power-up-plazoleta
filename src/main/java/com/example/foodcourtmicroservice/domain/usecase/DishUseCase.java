package com.example.foodcourtmicroservice.domain.usecase;


import com.example.foodcourtmicroservice.domain.api.IDishServicePort;
import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.exception.DishNoFoundException;
import com.example.foodcourtmicroservice.domain.exception.OwnerAnotherRestaurantException;
import com.example.foodcourtmicroservice.domain.model.Category;
import com.example.foodcourtmicroservice.domain.model.Dish;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.spi.ICategoryPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.example.foodcourtmicroservice.configuration.Constants;

import java.util.ArrayList;
import java.util.List;

public class DishUseCase implements IDishServicePort {
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IRestaurantExternalServicePort feignServicePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, ICategoryPersistencePort categoryPersistencePort, IRestaurantExternalServicePort feignServicePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.feignServicePort = feignServicePort;
    }
    @Override
    public void saveDish(Dish dish) {
        String rolUserActual = feignServicePort.getRoleFromToken(Token.getToken());
        ValidateAuthorization validateAuthorization = new ValidateAuthorization();
        validateAuthorization.validateRole(rolUserActual,Constants.ROLE_OWNER);

        dish.setActive(true);

        Long idOwner = Long.parseLong(feignServicePort.getIdOwnerFromToken(Token.getToken()));
        Restaurant restaurant = restaurantPersistencePort.getRestaurant(dish.getIdRestaurant().getId());
        if(idOwner.equals(restaurant.getIdOwner())){
            dish.setIdRestaurant(restaurant);
        } else{
            throw new OwnerAnotherRestaurantException(Constants.DIFFERENT_OWNER);
        }

        Category category = categoryPersistencePort.getCategory(dish.getIdCategory().getId());
        dish.setIdCategory(category);

        this.dishPersistencePort.saveDish(dish);
    }
    @Override
    public void updateDish(Long id,String price, String description) {
        String rolUserActual = feignServicePort.getRoleFromToken(Token.getToken());
        ValidateAuthorization validateAuthorization = new ValidateAuthorization();
        validateAuthorization.validateRole(rolUserActual,Constants.ROLE_OWNER);

        Dish dish = dishPersistencePort.getDish(id);

        Long idOwner = Long.parseLong(feignServicePort.getIdOwnerFromToken(Token.getToken()));
        if(!idOwner.equals(dish.getIdRestaurant().getIdOwner())) {
            throw new OwnerAnotherRestaurantException(Constants.DIFFERENT_OWNER);
        }
        if(price != null){
            dish.setPrecio(price);
        }
        if(description != null){
            dish.setDescripcion(description);
        }
        dishPersistencePort.updateDish(dish);
    }

    @Override
    public void updateEnableDisableDish(Long dishId, Long enableDisable) {
        Dish dishModel= dishPersistencePort.getDish(dishId);
        if(dishModel==null) throw new DishNoFoundException(Constants.UNREGISTERED_DISH);
        String rolUserActual = feignServicePort.getRoleFromToken(Token.getToken());
        ValidateAuthorization validateAuthorization = new ValidateAuthorization();
        validateAuthorization.validateRole(rolUserActual,Constants.ROLE_OWNER);

        Long idOwner = Long.parseLong(feignServicePort.getIdOwnerFromToken(Token.getToken()));
        if(!idOwner.equals(dishModel.getIdRestaurant().getIdOwner())) {
            throw new OwnerAnotherRestaurantException(Constants.DIFFERENT_OWNER);
        }

        boolean isEnableOrDisable = (enableDisable==1)?true:false;
        dishModel.setActive(isEnableOrDisable);
        dishPersistencePort.saveDish(dishModel);
    }

    @Override
    public List<Dish> findAllByRestaurantId(Long idRestaurante, Integer page, Integer size) {

        List<Dish> dishList=dishPersistencePort.findAllByRestaurantId(idRestaurante, page,size);
        List<Dish> ActiveDishes=new ArrayList<>();
        for (Dish dish:dishList) {
            if(dish.getActive()){
                ActiveDishes.add(dish);
            }
        }
        return ActiveDishes;
    }

    @Override
    public List<Dish> findAllByRestaurantIdAndCategory(Long idRestaurante, String category, Integer page, Integer size) {
        List<Dish> dishList=dishPersistencePort.findAllByRestaurantIdAndCategory(category,idRestaurante,page,size);
        List<Dish> ActiveDishes=new ArrayList<>();
        for (Dish dish:dishList) {
            if(dish.getActive()){
                ActiveDishes.add(dish);
            }
        }
        return ActiveDishes;
    }
}
