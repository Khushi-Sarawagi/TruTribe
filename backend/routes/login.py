from flask import Flask, request, jsonify
import psycopg2
import bcrypt

app = Flask(__name__)

def connect_db():
    return psycopg2.connect(
        dbname="  ",
        user="  ",
        password="  ",
        host="  ",
        port="  "
    )

@app.route('/login', methods=['POST'])
def login():
    data = request.json
    username = data.get('username')
    password = data.get('password')

    conn = connect_db()
    cursor = conn.cursor()

    cursor.execute("SELECT password FROM users WHERE username = %s", (username,))
    user = cursor.fetchone()

    if user:
        stored_password = user[0]
        if bcrypt.checkpw(password.encode('utf-8'), stored_password.encode('utf-8')):
            return jsonify({"message": "Login successful!"}), 200
        else:
            return jsonify({"message": "Incorrect password"}), 401
    else:
        return jsonify({"message": "User not found"}), 404

    cursor.close()
    conn.close()

if __name__ == '__main__':
    app.run(debug=True)
