package com.example.foodcourtmicroservice.domain.usecase;


import com.example.foodcourtmicroservice.domain.api.IDishServicePort;
import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.exception.OwnerAnotherRestaurantException;
import com.example.foodcourtmicroservice.domain.model.Category;
import com.example.foodcourtmicroservice.domain.model.Dish;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.spi.ICategoryPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.example.foodcourtmicroservice.configuration.Constants;

public class DishUseCase implements IDishServicePort {
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final ICategoryPersistencePort categoriaPersistencePort;
    private final IRestaurantExternalServicePort feignServicePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, ICategoryPersistencePort categoriaPersistencePort, IRestaurantExternalServicePort feignServicePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.categoriaPersistencePort = categoriaPersistencePort;
        this.feignServicePort = feignServicePort;
    }
    @Override
    public void saveDish(Dish dish) {
        String rolUserActual = feignServicePort.getRolFromToken(Token.getToken());
        ValidateAuthorization validateAuthorization = new ValidateAuthorization();
        validateAuthorization.validarRol(rolUserActual,Constants.ROLE_OWNER);

        dish.setActivo(true);

        Long idOwner = Long.parseLong(feignServicePort.getIdOwnerFromToken(Token.getToken()));
        Restaurant restaurant = restaurantPersistencePort.getRestaurant(dish.getIdRestaurantAux());
        if(idOwner.equals(restaurant.getIdOwner())){
            dish.setIdRestaurant(restaurant);
        } else{
            throw new OwnerAnotherRestaurantException(Constants.DIFFERENT_OWNER);
        }

        Category category = categoriaPersistencePort.getCategory(dish.getIdCategoryAux());
        dish.setIdCategory(category);

        this.dishPersistencePort.saveDish(dish);
    }
    @Override
    public void updateDish(Long id,String price, String description) {
        String rolUserActual = feignServicePort.getRolFromToken(Token.getToken());
        ValidateAuthorization validateAuthorization = new ValidateAuthorization();
        validateAuthorization.validarRol(rolUserActual,Constants.ROLE_OWNER);

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
}
