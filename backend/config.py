

import os
from dotenv import load_dotenv

# Load environment variables from .env file
load_dotenv()

class Config:
    SECRET_KEY = os.getenv("SECRET_KEY", "fallback_secret_key")  # Secure key for session management
    SQLALCHEMY_DATABASE_URI = os.getenv("DATABASE_URL", "postgresql://payel:pay187@localhost/signup_portal")
    SQLALCHEMY_TRACK_MODIFICATIONS = False
