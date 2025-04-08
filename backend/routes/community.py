from flask import Flask, jsonify, request
from flask_sqlalchemy import SQLAlchemy
import os

app = Flask(__name__)


app.config['SQLALCHEMY_DATABASE_URI'] = 'postgresql://communityuser:community@localhost/trutribeCommunities'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)


class Community(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(100), nullable=False)
    icon_url = db.Column(db.String(255), nullable=False)
    color = db.Column(db.String(7), nullable=False)

    def to_dict(self):
        return {
            "id": self.id,
            "name": self.name,
            "icon_url": self.icon_url,
            "color": self.color
        }

@app.route('/')
def home():
    return "TruTribe Communities"

@app.route('/communities', methods=['GET'])
def get_communities():
    communities = Community.query.all()
    return jsonify([community.to_dict() for community in communities])

@app.route('/communities/trending', methods=['GET'])
def trending_communities():
    trending = Community.query.limit(5).all()
    return jsonify([community.to_dict() for community in trending])

@app.route('/communities/suggested', methods=['GET'])
def suggested_communities():
    suggested = Community.query.offset(5).limit(5).all()
    return jsonify([community.to_dict() for community in suggested])

if __name__ == '__main__':
    app.run(debug=True, port=5001)



# INSERT INTO community (name, icon_url, color) VALUES
# ('Booklovers', 'https://github.com/Khushi-Sarawagi/TruTribe/blob/communitypage/app/src/main/res/drawable/book_lover.xml', '#E6C9A8'),
# ('GymFreaks', 'https://github.com/Khushi-Sarawagi/TruTribe/blob/communitypage/app/src/main/res/drawable/gym_freak.xml', '#A0D8EF'),
# ('Coders', 'https://github.com/Khushi-Sarawagi/TruTribe/blob/communitypage/app/src/main/res/drawable/coders.xml', '#F8E8A6'),
# ('Yoga Enthusiasts', 'https://github.com/Khushi-Sarawagi/TruTribe/blob/communitypage/app/src/main/res/drawable/yoga_enthusiast.xml', '#A8D5BA'),
# ('Dancers', 'https://github.com/Khushi-Sarawagi/TruTribe/blob/communitypage/app/src/main/res/drawable/dancers.xml', '#F4A7B9'),
# ('Sports Fans', 'https://github.com/Khushi-Sarawagi/TruTribe/blob/communitypage/app/src/main/res/drawable/sports_fan.xml', '#FBD210'),
# ('Music Lovers', 'https://github.com/Khushi-Sarawagi/TruTribe/blob/communitypage/app/src/main/res/drawable/music.xml', '#D6A2E8'),
# ('Cooks & Chefs', 'https://github.com/Khushi-Sarawagi/TruTribe/blob/communitypage/app/src/main/res/drawable/cooking.xml', '#F4B400'),
# ('Travelers', 'https://github.com/Khushi-Sarawagi/TruTribe/blob/communitypage/app/src/main/res/drawable/travelers.xml', '#50C4ED'),
# ('Artists', 'https://github.com/Khushi-Sarawagi/TruTribe/blob/communitypage/app/src/main/res/drawable/artist.xml', '#FFB6C1');



