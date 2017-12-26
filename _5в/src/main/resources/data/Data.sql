USE applecompany;

INSERT INTO apple (apple_name, inventor, country, year, amount) VALUES
('Adams Parman', 'N. Pepin','England', 1826, 5),
('Gorod', 'O. Kozak ', 'Ukraine', 2014, 4),
('Java', 'Z. Veres','Ukraine', 2006,10),
('Povtorka', 'Z. Veres','Ukraine', 20016, 100),
('SmartBoy', 'Y. Pasternak', 'Ukraine', 2004, 1);

INSERT INTO city (city_id, city) VALUES
(1,'Lviv'),(2,'Kyiv'),(3,'Poltava'),(4,'Rivne'),(5,'Ternopil');

INSERT INTO warehouse (warehousename, logo, email, city_id, street, apartment) VALUES
('VeresLvivApple', 'Java apples', 'veres@gmail.com', 3, 'S.Bandery', '2'),
('PastornakAppleCompany', 'Popushcheni Yabluka', 'pastor@gmail.com', 4, 'Levanda', '11/14a'),
('KozakEnterprises', 'Tobi yabluka, tikay zi skladu', 'kozakseller@gmail.com', 1, 'Gorodotska','23');
