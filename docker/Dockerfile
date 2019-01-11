FROM payara/server-full:5.184

#setup
COPY ./postgresql-42.2.5.jar /opt/payara/appserver/glassfish/domains/production/lib/postgresql.jar

COPY domain.xml /opt/payara/appserver/glassfish/domains/production/config/domain.xml
COPY login.conf /opt/payara/appserver/glassfish/domains/production/config/login.conf
COPY rnkrealm-0.2.jar /opt/payara/appserver/glassfish/domains/production/lib/rnkrealm.jar
COPY --chown=payara:payara create-realm.sh /opt/payara/scripts/create-realm.sh

EXPOSE 42333