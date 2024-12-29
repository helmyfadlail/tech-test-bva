FROM node:21-alpine as build

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build 

FROM nginx:latest

COPY --from=build /app/dist /usr/share/nginx/html 

EXPOSE 3000