drop database if exists prs;
create database prs;
use prs;

create table User (
	ID				int				primary key auto_increment,
    UserName		varchar(20)		not null unique,
    Password		varchar(10)		not null,
    FirstName		varchar(20)		not null,
    LastName		varchar(20)		not null,
    PhoneNumber		varchar(12)		not null,
    Email			varchar(75)		not null,
    IsReviewer		tinyint(1)		not null default 0,
    IsAdmin			tinyint(1)		not null default 0
    );
    
create table Vendor (
	ID			int				primary key auto_increment,
    Code		varchar(10)		not null unique,
    Name		varchar(255)	not null,
    Address		varchar(255)	not null,
    City		varchar(255)	not null,
    State		varchar(2)		not null,
    Zip			varchar(5)		not null,
    PhoneNumber	varchar(12)		not null,
    Email		varchar(100)	not null
    );    

create table PurchaseRequest (
	ID					int				primary key auto_increment,
    UserID				int				not null,
    Description			varchar(100)	not null,
    Justification		varchar(255)	not null,
    DateNeeded			date			not null,
    DeliveryMode		varchar(25)		not null,
    Status				varchar(20)		not null,
    Total				decimal(10,2)	not null,
    SubmittedDate		datetime		not null,
    ReasonForRejection	varchar(100),
    foreign key (UserID) references User(ID)
    );
    
create table Product (
	ID			int				primary key auto_increment,
    VendorID	int				not null unique,
    PartNumber	varchar(50)		not null unique,
    Name		varchar(150)	not null,
    Price		decimal(10,2)	not null,
    Unit		varchar(255),
    PhotoPath	varchar(255),
    foreign key (VendorID) references Vendor(ID)
    );
    
create table PurchaseRequestLineItem (
	ID					int		primary key auto_increment,
    PurchaseRequestID	int		not null unique,
    ProductID			int		not null unique,
    Quantity			int		not null,
    foreign key (PurchaseRequestID) references PurchaseRequest(ID),
    foreign key (ProductID) references Product(ID)
    );
    
insert User (UserName, Password, FirstName, LastName, PhoneNumber, Email, IsReviewer, IsAdmin)
	Values ('kkreanne', 'OneHeart27', 'Karlee', 'Abrams', '513-608-1672', 'karkar1030@yahoo.com', '0', '0');
insert User (UserName, Password, FirstName, LastName, PhoneNumber, Email, IsReviewer, IsAdmin)
	Values ('tomsterj', 'Boston3', 'Tommy', 'Johnson', '513-714-1772', 'tomsterj1@yahoo.com', '1', '0');
    
insert Vendor (Code, Name, Address, City, State, Zip, PhoneNumber, Email)
	Values ('1234', 'Dunham Supplies', '249 Main St', 'Cincinnati', 'OH', '45248', '513-123-4567', 'dunhamsupplies@yahoo.com');
insert Vendor (Code, Name, Address, City, State, Zip, PhoneNumber, Email)
	Values ('2345', 'Bolder Stream', '45 Glenway Ave', 'Cincinnati', 'OH', '45238', '513-890-1234', 'BolderStreamVending@fuse.net');