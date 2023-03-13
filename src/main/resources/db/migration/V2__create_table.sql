CREATE TABLE IF NOT EXISTS pharmacy.user (
  id VARCHAR(255) PRIMARY KEY NOT NULL,
  username VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS pharmacy.role(
  id VARCHAR(255) PRIMARY KEY NOT NULL
);

INSERT INTO pharmacy.role VALUES('admin');
INSERT INTO pharmacy.role VALUES('user');

CREATE TABLE IF NOT EXISTS pharmacy.user_role_association(
 user_id VARCHAR(255) REFERENCES pharmacy.user(id) NOT NULL,
  role_id VARCHAR(255) REFERENCES pharmacy.role(id) NOT NULL
);
ALTER TABLE pharmacy.user_role_association ADD CONSTRAINT user_role_association_pk PRIMARY KEY (user_id, role_id);

CREATE TABLE IF NOT EXISTS pharmacy.product_unit (
  id VARCHAR(255) PRIMARY KEY NOT NULL
);

INSERT INTO pharmacy.product_unit VALUES('pack');
INSERT INTO pharmacy.product_unit VALUES('bottle');
INSERT INTO pharmacy.product_unit VALUES('box');
INSERT INTO pharmacy.product_unit VALUES('capsule');
INSERT INTO pharmacy.product_unit VALUES('tube');

CREATE TABLE IF NOT EXISTS pharmacy.product (
  id VARCHAR(255) PRIMARY KEY NOT NULL,
  name VARCHAR(255) UNIQUE NOT NULL,
  description VARCHAR(255),
  base_unit_id VARCHAR(255) NOT NULL REFERENCES pharmacy.product_unit(id),
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS pharmacy.product_type (
  id VARCHAR(255) PRIMARY KEY NOT NULL
);

INSERT INTO pharmacy.product_type VALUES('amphetamine');
INSERT INTO pharmacy.product_type VALUES('anabolic steroid');
INSERT INTO pharmacy.product_type VALUES('anaesthesia');
INSERT INTO pharmacy.product_type VALUES('cough syrup');
INSERT INTO pharmacy.product_type VALUES('drops');
INSERT INTO pharmacy.product_type VALUES('herbal medicine');
INSERT INTO pharmacy.product_type VALUES('inhalant');
INSERT INTO pharmacy.product_type VALUES('injection');
INSERT INTO pharmacy.product_type VALUES('laxative');
INSERT INTO pharmacy.product_type VALUES('narcotic');
INSERT INTO pharmacy.product_type VALUES('painkiller');

CREATE TABLE IF NOT EXISTS pharmacy.product_type_association (
  product_id VARCHAR(255) UNIQUE NOT NULL REFERENCES pharmacy.product(id),
  product_type_id VARCHAR(255) UNIQUE NOT NULL REFERENCES pharmacy.product_type(id)
);
ALTER TABLE pharmacy.product_type_association ADD CONSTRAINT product_type_association_pk PRIMARY KEY (product_id, product_type_id);

CREATE TABLE IF NOT EXISTS pharmacy.imported_product (
  id VARCHAR(255) PRIMARY KEY NOT NULL,
  product_id VARCHAR(255) UNIQUE NOT NULL REFERENCES pharmacy.product_type(id),
  imported_date DATE NOT NULL,
  expiration_date DATE NOT NULL,
  unit_id VARCHAR(255) NOT NULL REFERENCES pharmacy.product_unit(id),
  quantity DECIMAL(10, 2) NOT NULL,
  cost DECIMAL(19,4) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS pharmacy.product_unit_conversion(
  unit_id VARCHAR(255) NOT NULL REFERENCES pharmacy.product_unit(id),
  product_id VARCHAR(255) NOT NULL REFERENCES pharmacy.product(id),
  conversion_factor DECIMAL(10, 2) NOT NULL,
  listing_price DECIMAL(19,4) NOT NULL
);
ALTER TABLE pharmacy.product_unit_conversion ADD CONSTRAINT product_unit_conversion_pk PRIMARY KEY (unit_id, product_id);

CREATE TABLE IF NOT EXISTS pharmacy.event (
  id VARCHAR(255) PRIMARY KEY NOT NULL,
  name VARCHAR(255) UNIQUE NOT NULL,
  description VARCHAR(255) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS pharmacy.event_rule (
  id VARCHAR(255) PRIMARY KEY NOT NULL,
  event_id VARCHAR(255) UNIQUE NOT NULL,
  rule   VARCHAR(255) UNIQUE NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS pharmacy.order_item (
  id VARCHAR(255) NOT NULL PRIMARY KEY,
  sell_unit_id VARCHAR(255) NOT NULL REFERENCES pharmacy.product_unit(id),
  product_id  VARCHAR(255) NOT NULL REFERENCES pharmacy.product(id),
  quantity  DECIMAL(10, 2) NOT NULL,
  price DECIMAL(19,4) NOT NULL,
  event_id VARCHAR(255) NOT NULL REFERENCES pharmacy.event(id)
);

CREATE TABLE IF NOT EXISTS pharmacy.payment_method (
  id VARCHAR(255) NOT NULL PRIMARY KEY
);

INSERT INTO pharmacy.payment_method VALUES('card');
INSERT INTO pharmacy.payment_method VALUES('bank_transfer');
INSERT INTO pharmacy.payment_method VALUES('cod');
INSERT INTO pharmacy.payment_method VALUES('card');
INSERT INTO pharmacy.payment_method VALUES('tp_portal');

CREATE TABLE IF NOT EXISTS pharmacy.order (
  id VARCHAR(255) NOT NULL PRIMARY KEY,
  event_id VARCHAR(255) NOT NULL REFERENCES pharmacy.event(id),
  price DECIMAL(19,4) NOT NULL,
  payment_method_id VARCHAR(255) NOT NULL REFERENCES pharmacy.payment_method(id),
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL
);

-- Each payment method have different table to store different payment data association
-- TODO: Create different payment association table

CREATE TABLE IF NOT EXISTS pharmacy.order_item_association (
  order_id VARCHAR(255) NOT NULL REFERENCES pharmacy.order(id),
  order_item_id VARCHAR(255) NOT NULL REFERENCES pharmacy.order_item(id)
);

CREATE TABLE IF NOT EXISTS pharmacy.delivery_partner (
  id VARCHAR(255) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS pharmacy.delivery (
  id VARCHAR(255) NOT NULL PRIMARY KEY,
  order_id VARCHAR(255) NOT NULL REFERENCES pharmacy.order(id),
  tracking_id VARCHAR(255) NOT NULL,
  delivery_partner_id VARCHAR(255) NOT NULL REFERENCES pharmacy.delivery_partner(id),
  delivery_status_id VARCHAR(255) NOT NULL REFERENCES pharmacy.delivery_status(id)
);

CREATE TABLE IF NOT EXISTS pharmacy.delivery_status(
    id VARCHAR(255) NOT NULL PRIMARY KEY,
)
INSERT INTO pharmacy.delivery_status('pending');
INSERT INTO pharmacy.delivery_status VALUES('in_delivery');
INSERT INTO pharmacy.delivery_status VALUES('delivered');
INSERT INTO pharmacy.delivery_status VALUES('failed');
