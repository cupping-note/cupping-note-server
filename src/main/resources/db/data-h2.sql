-- Users 더미 데이터 생성
INSERT INTO users (`id`, `email`, `nickname`, `profile_image_url`, `oauth_platform`, `role`, `created_at`, `updated_at`, `deleted_at`)
VALUES (1, 'white_gyu@naver.com', 'hbk', null, 'KAKAO', 'USER', '2023-02-15 22:48:59', '2023-02-15 22:48:59', null);

-- Coffeebean 더미 데이터 생성
INSERT INTO coffee_bean (`id`, `name`, `aroma`, `acidity`, `sweetness`, `bitterness`, `body`, `image_url`, `nation`, `feature`, `created_at`, `updated_at`, `deleted_at`)
VALUES (1, '인도네시아 자바 G1', '4', '1', '2', '1', '5', 'https://cupping.cpglcdn.com/images/coffeebean/indonesia_java_g1.png', '인도네시아', '맛있음', '2023-02-15 22:48:59', '2023-02-15 22:48:59', null);

INSERT INTO coffee_bean (`id`, `name`, `aroma`, `acidity`, `sweetness`, `bitterness`, `body`, `image_url`, `nation`, `feature`, `created_at`, `updated_at`, `deleted_at`)
VALUES (2, '인도네시아 발리 칸타마니', '1', '5', '2', '4', '3', 'https://cupping.cpglcdn.com/images/coffeebean/indonesia_bali_kantamani.png', '인도네시아', '맛없음', '2023-02-15 22:48:59', '2023-02-15 22:48:59', null);

INSERT INTO coffee_bean (`id`, `name`, `aroma`, `acidity`, `sweetness`, `bitterness`, `body`, `image_url`, `nation`, `feature`, `created_at`, `updated_at`, `deleted_at`)
VALUES (3, '브라질 산토스 디카페인', '4', '5', '2', '4', '4', 'https://cupping.cpglcdn.com/images/coffeebean/brasil_santos_decaffein.png', '브라질', '맛이 풍부함', '2023-02-15 22:48:59', '2023-02-15 22:48:59', null);
