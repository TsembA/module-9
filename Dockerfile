FROM node:18

WORKDIR /usr/src/app/app
COPY . .
COPY app/package*.json ./
RUN npm install
EXPOSE 3000
CMD ["node", "src/server.js"]

