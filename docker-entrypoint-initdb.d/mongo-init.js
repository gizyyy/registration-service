print("Started Adding the User.");
db = db.getSiblingDB("admin");
db.createUser({
  user: "admin",
  pwd: "password",
  roles: [{ role: "userAdminAnyDatabase", db: "admin" }]
});
print("End Adding the User Role.");