from flask import Blueprint, request, jsonify
from flask_cors import CORS  # Import CORS
from models.model import db, User  # Import database and User model
from werkzeug.security import generate_password_hash

signup_bp = Blueprint("signup", __name__)
CORS(signup_bp)  # Enable CORS for this blueprint

@signup_bp.route("/signup", methods=["POST"])
def signup():
    data = request.get_json()
    print("Received data:", data)  # Debugging line

    if not data:
        return jsonify({"error": "No data received"}), 400

    username = data.get("username")
    email = data.get("email")
    password = data.get("password")

    if not username or not email or not password:
        return jsonify({"error": "All fields are required"}), 400

    print(f"Username: {username}, Email: {email}, Password: {password}")

    if User.query.filter_by(email=email).first():
        return jsonify({"error": "Email already registered"}), 409

    hashed_password = generate_password_hash(password, method="pbkdf2:sha256")
    print("Generated hash:", hashed_password)

    new_user = User(username=username, email=email, password=hashed_password)
    db.session.add(new_user)
    db.session.commit()
    print("User created successfully")

    return jsonify({"message": "User created successfully"}), 201
