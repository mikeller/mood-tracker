FROM node:24 as build
RUN npm install -g @angular/cli
WORKDIR /opt/app
COPY MoodTracker/package*.json ./
RUN npm install
COPY MoodTracker/ ./
RUN npm run build --omit=dev

FROM nginx:latest
COPY --from=build /opt/app/dist/mood-tracker /usr/share/nginx/html

