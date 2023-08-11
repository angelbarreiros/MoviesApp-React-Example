package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.User;

public class UserConversor {
	
	private UserConversor() {}
	
	public static UserDto toUserDto(User user) {
		return new UserDto(user.getId(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getEmail(),
			user.getRole().toString());
	}
	
	public static User toUser(UserDto userDto) {
		
		return new User(userDto.getUserName(), userDto.getPassword(), userDto.getFirstName(), userDto.getLastName(),
			userDto.getEmail());
	}
	
	public static AuthenticatedUserDto toAuthenticatedUserDto(String serviceToken, User user) {
		
		return new AuthenticatedUserDto(serviceToken, toUserDto(user));
		
	}

}
