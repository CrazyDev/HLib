# HLib
Como usar Warns
```java
        Warns warns = new Warns();
        Warns.Warn warn = warns.build("My Warn").setDuration(20 * 1000).setWarns(new long[]{20 * 1000, 15 * 1000, 10 * 1000, 5 * 1000, 4 * 1000, 3 * 1000, 2 * 1000, 1 * 1000});
        Warns.Warn.Result result2;

        while (!(result2 = warn.tryWarn()).isComplete()) {
            if (result2.isWarned()) {
                System.out.println("Tempo restante -> " + result2.getRemaingTimeOfWarns() / 1000);
            }
        }
```

Saída
```lang
Tempo restante -> 20
Tempo restante -> 15
Tempo restante -> 10
Tempo restante -> 5
Tempo restante -> 4
Tempo restante -> 3
Tempo restante -> 2
Tempo restante -> 1
```
