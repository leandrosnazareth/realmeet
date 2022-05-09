package br.com.sw2you.realmeet.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorsConfiguration {

    //definir tamanho das thread, tamanho das filas de threads para evitar sobrecarga no servidor e envitar que seu servidor seja derrubado
    //esses parmetros serao passados no application.yml
    @Bean
    public Executor controllerExecutor(
            //anotacao @value para pegar p valor do arquivo application.yml se nao tiver definido no yml assume o valor :xx
            @Value("${realmeet.taskExecutor.pool.coreSize:10}") int corePoolSize,
            @Value("${realmeet.taskExecutor.pool.maxSize:20}") int maxPoolSize,
            @Value("${realmeet.taskExecutor.pool.queueCapacity:50}") int queueCapacity,
            @Value("${realmeet.taskExecutor.pool.keepAliveSeconds:60}") int keepAliveSeconds) {
        return new ThreadPoolExecutor(
                corePoolSize,//tamanho basico do poll quando ele é criado
                maxPoolSize, // maximo de threads
                keepAliveSeconds, //quantos segundos cada thread vai ficar viva
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueCapacity, true));
    }
}
