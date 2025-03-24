from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_bcrypt import Bcrypt
from flask_cors import CORS
from config import Config  # Import configuration from config.py
from models.model import db, bcrypt
from routes.signup import signup_bp
from routes.login import login_bp

app = Flask(__name__)
CORS(app)  # Enable CORS for frontend requests

# Use configurations from Config class
app.config.from_object(Config)

# Initialize extensions
db.init_app(app)
bcrypt.init_app(app)

# Register Blueprints
app.register_blueprint(signup_bp)
app.register_blueprint(login_bp)

with app.app_context():
    db.create_all()  # Ensure tables are created

if __name__ == "__main__":
    app.run(debug=True)
