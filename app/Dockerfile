FROM node:18

WORKDIR /usr/src/app
COPY . .
COPY app/package*.json ./
RUN npm install
EXPOSE 3000
CMD ["node", "/usr/src/app/app/server.js"]

