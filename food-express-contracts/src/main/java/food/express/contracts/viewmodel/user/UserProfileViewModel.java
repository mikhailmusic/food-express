package food.express.contracts.viewmodel.user;

import food.express.contracts.viewmodel.BaseViewModel;


public record UserProfileViewModel(
        BaseViewModel base,
        UserViewModel user
) {}
