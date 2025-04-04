from flask import Flask, jsonify, request
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)


import os
app.config['SQLALCHEMY_DATABASE_URI'] = os.getenv('DATABASE_URL', 'postgresql://communityuser:community@localhost/trutribeCommunities')

app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False


db = SQLAlchemy(app)

class Community(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(100), nullable=False)
    icon_url = db.Column(db.String(255), nullable=False)
    color = db.Column(db.String(7), nullable=False)

    def comu(self):
        return {
            "id": self.id,
            "name": self.name,
            "icon_url": self.icon_url,
            "color": self.color
        }


@app.route('/communities', methods=['GET'])
def communities():
    communities = Community.query.all()
    return jsonify([community.comu() for community in communities])


@app.route('/communities/trending', methods=['GET'])
def trendingCommunities():

    trendingCommunities = Community.query.limit(5).all()
    return jsonify([community.comu() for community in trendingCommunities])


@app.route('/communities/suggested', methods=['GET'])
def suggestedCommunities():

    suggestedCommunities = Community.query.offset(5).limit(5).all()
    return jsonify([community.comu() for community in suggestedCommunities])


if __name__ == '__main__':
    app.run(debug=True)






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



