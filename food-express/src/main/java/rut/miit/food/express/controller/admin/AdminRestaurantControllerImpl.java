package rut.miit.food.express.controller.admin;

import food.express.contracts.controller.admin.AdminRestaurantController;
import food.express.contracts.form.DishCreateForm;
import food.express.contracts.form.RestaurantCreateForm;
import food.express.contracts.form.RestaurantEditForm;
import food.express.contracts.viewmodel.CreateViewModel;
import food.express.contracts.viewmodel.EditViewModel;
import food.express.contracts.viewmodel.category.CategoryViewModel;
import food.express.contracts.viewmodel.dish.DishInputViewModel;
import food.express.contracts.viewmodel.order.OrderItemViewModel;
import food.express.contracts.viewmodel.order.OrderRestaurantListViewModel;
import food.express.contracts.viewmodel.order.OrderRestaurantViewModel;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rut.miit.food.express.controller.BaseControllerImpl;
import rut.miit.food.express.dto.dish.DishAddDto;
import rut.miit.food.express.dto.order.OrderDto;
import rut.miit.food.express.dto.restaurant.RestaurantAddDto;
import rut.miit.food.express.dto.restaurant.RestaurantDto;
import rut.miit.food.express.service.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/restaurants")
public class AdminRestaurantControllerImpl extends BaseControllerImpl implements AdminRestaurantController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    private RestaurantService restaurantService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Override
    @PostMapping("/add")
    public String addRestaurant(@Valid @ModelAttribute("form") RestaurantCreateForm form, BindingResult result, Model model, Principal principal) {
        LOG.info("ADMIN {} is attempting to add a new restaurant with name: {}", principal.getName(), form.name());

        if (result.hasErrors()) {
            CreateViewModel viewModel = new CreateViewModel(
                    createBaseViewModel("Добавление ресторана")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "restaurant-add";
        }
        RestaurantAddDto dto = new RestaurantAddDto(form.name(), form.address(), form.description(), form.phoneNumber(),
                form.openTime(), form.closeTime(), form.minOrderAmount());
        Integer id = restaurantService.registerRestaurant(dto);
        LOG.info("ADMIN {} successfully added new restaurant ID: {}, {}", principal.getName(), id, form);
        return "redirect:/restaurants/" + id;
    }

    @Override
    @PostMapping("/{id}/edit")
    public String editRestaurant(@PathVariable Integer id, @Valid @ModelAttribute("form") RestaurantEditForm form, BindingResult result, Model model, Principal principal) {
        LOG.info("ADMIN {} is attempting to edit restaurant with id: {}", principal.getName(), id);

        if (result.hasErrors()) {
            EditViewModel viewModel = new EditViewModel(
                    createBaseViewModel("Редактирование информации")
            );
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "restaurant-edit";
        }
        RestaurantDto dto = new RestaurantDto(id, form.name(), form.address(), form.description(), form.phoneNumber(), form.openTime(), form.closeTime(), form.minOrderAmount());
        restaurantService.changeRestaurantInfo(dto);
        LOG.info("ADMIN {} successfully edited restaurant: {}", principal.getName(), form);
        return "redirect:/restaurants/" + id;
    }

    @Override
    @GetMapping("/{id}/edit")
    public String editRestaurant(@PathVariable Integer id, Model model, Principal principal) {
        LOG.info("ADMIN {} is viewing edit form for restaurant ID: {}", principal.getName(), id);
        RestaurantDto dto = restaurantService.getRestaurantDetails(id);
        EditViewModel viewModel = new EditViewModel(
                createBaseViewModel("Изменение информации")
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new RestaurantEditForm(dto.id(), dto.name(), dto.address(), dto.description(), dto.phoneNumber(), dto.openTime(), dto.closeTime(), dto.minOrderAmount()));
        return "restaurant-edit";
    }

    @Override
    @GetMapping("/add")
    public String addRestaurant(Model model, Principal principal) {
        LOG.info("ADMIN {} is viewing form to add a new restaurant", principal.getName());
        CreateViewModel viewModel = new CreateViewModel(
                createBaseViewModel("Добавление ресторана")
        );
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new RestaurantCreateForm("", "", "", "", null, null, BigDecimal.ZERO));
        return "restaurant-add";
    }

}
