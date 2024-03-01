package com.example.nagoyameshi.seeder;

import java.util.List;
import java.util.Random;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Component
public class DataSeeder implements ApplicationRunner {
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public DataSeeder(ReviewRepository reviewRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long count = reviewRepository.count();
        
        // テーブルが空の場合にのみテストデータを作成する
        if (count == 0) {
            List<Restaurant> restaurants = restaurantRepository.findAll();
            List<User> members = userRepository.findByRoleNameInRoleFreeMemberOrRolePaidMember(); 
            int numberOfRestaurants = restaurants.size();
            int numberOfMembers = members.size();
            Random rand = new Random();
            
            for (int i = 1; i <= 500; i++) {
                int randomNumberRestaurant = rand.nextInt(numberOfRestaurants);
                int randomNumberMember = rand.nextInt(numberOfMembers);
                Restaurant restaurant = restaurants.get(randomNumberRestaurant);
                User member = members.get(randomNumberMember);
                
                // 同じrestaurant_idとuser_idの組み合わせが存在しなければ、テストデータを作成する
                if (reviewRepository.findByRestaurantAndUser(restaurant, member) == null) {
                    Review review = new Review();
                    String content = "名古屋では有名な格安で焼肉食べ放題のお店。タイミングよく仕事で行く機会があったので、地元の友人と一緒に来店しました。店内は広くゆったりとできます。";
                    int score = rand.nextInt(5) + 1;  
                    
                    review.setContent(content);
                    review.setScore(score);
                    review.setRestaurant(restaurant);
                    review.setUser(member);
                    reviewRepository.save(review);                    
                }
            }
        }
    }
}
