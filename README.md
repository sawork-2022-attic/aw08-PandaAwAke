# aw08

Run the project with `mvn spring-boot:run` and send request to `http://localhost:8080/check`. You should see an reponses in json format like the following.

```json
{
    "icon_url": "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
    "id": "kswv7NIaTCaIIErlBzODaA",
    "url": "https://api.chucknorris.io/jokes/kswv7NIaTCaIIErlBzODaA",
    "value": "Chuck Norris's shadow weighs 250 pounds and can kick your ass ."
}
```

Try to understand the provided code which demonstrates spring integration between a spring boot application with an externel http service (https://api.chucknorris.io/jokes/random).

Please implement delivery as a standalone service (just like the random joke service). Refer the sample code to integrate your Micropos system with delivery service so that user can check delivery status on Miropos which actually forwards user request to delivery service on demand.

![](Micropos.svg)

Consider the advantage by doing so and write it down in your readme file.



## 理解

* 首先这种HTTP交互提供了很好的一种机制以告知用户Delivery处理当前订单的情况如何
* 实现了一种**分离**，Delivery前面的部分只需要和Delivery及后面的部分进行规格化的HTTP交互，而无需关注Delivery及后面的部分对提供的信息做了什么
* 不需要订阅/通知，而采取了查询模式，避免了来回传递信息和建立联系的事件开销