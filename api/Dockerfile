FROM ruby:2.2

RUN apt-get update
RUN apt-get install -y nginx

ENV APP_ROOT /app
RUN mkdir -p $APP_ROOT/nginx/logs
WORKDIR $APP_ROOT

ENV PORT 80

ENV VCAP_SERVICES '{ \
      "user-provided": [ \
        { \
          "name": "feedback", \
          "credentials": { \
            "url":"feedback:8080" \
          } \
        }, \
        { \
          "name": "questions", \
          "credentials": { \
            "url":"questions:8080" \
          } \
        } \
      ] \
    }'

COPY nginx.conf $APP_ROOT/
COPY public $APP_ROOT/public/

RUN gem install erubis
RUN erb nginx.conf > /etc/nginx/nginx.conf

# forward request and error logs to docker log collector
RUN ln -sf /dev/stdout $APP_ROOT/nginx/logs/access.log
RUN ln -sf /dev/stderr $APP_ROOT/nginx/logs/error.log

EXPOSE $PORT

CMD ["nginx"]