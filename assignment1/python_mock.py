from flask import Flask, json

# companies = [{"id": 1, "name": "Company One"}, {"id": 2, "name": "Company Two"}]

app = Flask(__name__)

# @app.route('/companies', methods=['GET'])
# def get_companies():
#   return json.dumps(companies)


@app.route('/')
def hello_world():
    return 'Hello, World!'


if __name__ == '__main__':
    app.run()