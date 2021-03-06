package ru.geekbrains.trainingproject.market.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Data

// TODO Сейчас это довольно бесполезная статистика.
//  Надо добавить количество обращений и среднее время работы
//  сервисов и методов, если будет полезно можно хранить в  radis
//  если нет удалить так как на мой взгляд просто замедляет работу.
//  Вопрос нужна ли здесь ConcurrentHashMap? или можно обойтись обычной?


public class StatisticUtil {
    private ConcurrentHashMap<String, ConcurrentHashMap<String, Long>> serviceMap = new ConcurrentHashMap<>();

    public void setServiceMap(String serviceName, String methodName, Long duration) {
        //если уже были обе записи
        if (serviceMap.containsKey(serviceName) && serviceMap.get(serviceName).containsKey(methodName)) {
            Long tmpDuration = serviceMap.get(serviceName).get(methodName);
            serviceMap.get(serviceName).put(methodName, tmpDuration);
            return;
        }

        //если новый сервис
        if (!serviceMap.containsKey(serviceName)) {
            serviceMap.put(serviceName, new ConcurrentHashMap<>());
            serviceMap.get(serviceName).put(methodName, duration);
            return;
        }
        //если была запись о сервисе но новый метод
        if (serviceMap.containsKey(serviceName) && !serviceMap.get(serviceName).containsKey(methodName)) {
            serviceMap.get(serviceName).put(methodName, duration);
        }
    }

}
