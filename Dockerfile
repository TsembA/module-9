FROM node:18
WORKDIR /usr/src/
RUN npm install
COPY . .
EXPOSE 3000
CMD ["npm", "start"]
