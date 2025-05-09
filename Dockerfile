FROM node:18
WORKDIR /usr/src/app
COPY app /usr/src/app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 3000
CMD ["npm", "start"]
#1.0