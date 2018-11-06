/*
 * Copyright (C) 2018 Hugo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.gmail.hugosilvaf2.hlib.warns;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Hugo Silva <hugosilvaf2@gmail.com>
 */
public class Warns extends ConcurrentLinkedQueue<Warns.Warn> {

    public class Warn {

        public class Result {

            private long remaingTime = 0, remaingTimeOfWarns = 0;

            private boolean warned = false, isComplete = false;

            public Result() {

            }

            /**
             * Obtém o tempo real faltando
             *
             * @return
             */
            public long getRemaingTime() {
                return remaingTime;
            }

            /**
             * Obtém o tempo restante de acordo com o aviso
             *
             * @return
             */
            public long getRemaingTimeOfWarns() {
                return remaingTimeOfWarns;
            }

            /**
             * Retorna true se os avisos já estão completo
             *
             * @return
             */
            public boolean isComplete() {
                return isComplete;
            }

            /**
             * Retorna true se foi avisado.
             *
             * @return
             */
            public boolean isWarned() {
                return warned;
            }

            /**
             * Seta o tempo real que está faltando
             *
             * @param remaingTime
             * @return
             */
            public Result setRemaingTime(long remaingTime) {
                this.remaingTime = remaingTime;
                return this;
            }

            /**
             * Seta o tempo restando, de acordo com o aviso
             *
             * @param remaingTimeOfWarns
             */
            public void setRemaingTimeOfWarns(long remaingTimeOfWarns) {
                this.remaingTimeOfWarns = remaingTimeOfWarns;
            }

            /**
             * Seta se foi avisado
             *
             * @param warned
             * @return
             */
            public Result setWarned(boolean warned) {
                this.warned = warned;
                return this;

            }

            /**
             * Seta se foi completo os avisos
             *
             * @param isComplete
             * @return
             */
            public Result setIsComplete(boolean isComplete) {
                this.isComplete = isComplete;
                return this;
            }

        }

        private String name;

        private long lastTime = 0, firstTime = 0, duration = 0;
        /**
         * Aqui será os avisos por segundos Exemplo avisar quando tiver faltando
         * 10 segundos, 5,4,3,2,1... Lembrando que os avisos devem ser na ordem
         * decrescente. 5,4,3,2,1
         */
        private long[] warns;

        public Warn(String name) {
            this.name = name;
        }

        /**
         * Obtém o nome do aviso
         *
         * @return
         */
        public String getName() {
            return name;
        }

        /**
         * Ele checará se pode avisar, se retornar true pode, se false, não
         * pode. Utilize seu código para mandar os avisos se retornar true
         *
         * @return
         */
        public Result tryWarn() {
            Result result = new Result();
            if (isComplete() || getWarns().length == 0) {
                result.setWarned(false);
            }

            if (getWarns()[0] >= getRemaingTime()) {
                result.setRemaingTimeOfWarns(getWarns()[0]);
                setFirstTime(getFirstTime() != 0 ? getFirstTime() : System.currentTimeMillis()).setWarns(Arrays.copyOfRange(getWarns(), 1, getWarns().length));
                result.setWarned(true).setIsComplete(isComplete()).setRemaingTime(getRemaingTime());
            }

            return result;
        }

        /**
         * Retorna a primeira vez em que foi usado o Warn
         *
         * @return long Milisegundos
         */
        public long getFirstTime() {
            return firstTime;
        }

        /**
         * Retorna a útima vez em que foi usado o Warn
         *
         * @return long Milisegundos
         */
        public long getLastTime() {
            return lastTime;
        }

        /**
         * Retorna o tempo final do warn, quando ele não será mais usado
         *
         * @return long Milisegundos
         */
        public long getEndTime() {
            return (firstTime + duration);
        }

        /**
         * Retorna o tempo restante.
         *
         * @return long Milisegundos
         */
        public long getRemaingTime() {
            return ((getFirstTime() + duration) - System.currentTimeMillis());
        }

        /**
         * Retorna os segundos máximo que o warm irá durar.
         *
         * @return long milisegundos
         */
        public long getDuration() {
            return duration;
        }

        /**
         * Retorna os avisos, exemplo, irá avisar quando esiver faltando
         * 50,25,15,10,5,4,3,2,1 segundos
         *
         * @return
         */
        public long[] getWarns() {
            return warns;
        }

        public boolean isComplete() {
            return (getFirstTime() != 0 && System.currentTimeMillis() > getFirstTime() + getDuration());
        }

        /**
         * Seta a primeira vez que foi usado o aviso
         *
         * @param firstTime
         */
        public Warn setFirstTime(long firstTime) {
            this.firstTime = firstTime;
            return this;
        }

        /**
         * Seta a última vez que foi usado o aviso
         *
         * @param lastTime
         */
        public Warn setLastTime(long lastTime) {
            this.lastTime = lastTime;

            return this;
        }

        /**
         * Seta a duração do aviso
         *
         * @param duration
         */
        public Warn setDuration(long duration) {
            this.duration = duration;
            return this;
        }

        /**
         * Seta os avisos
         *
         * @param warns
         */
        public Warn setWarns(long[] warns) {
            this.warns = warns;
            return this;
        }

    }

    public Warns() {

    }

    /**
     * Obtém o Warn através do nome
     *
     * @param n
     * @return
     */
    public Warn getByName(String n) {
        return stream().filter(a -> a.getName().equals(n)).findFirst().get();
    }

    /**
     * Constroi um novo warn
     *
     * @return
     */
    public Warn build(String name) {
        return addWarn(new Warn(name));
    }

    /**
     * Add o warn no cache e retorna o mesmo
     *
     * @param w
     * @return
     */
    public Warn addWarn(Warn w) {
        add(w);
        return w;
    }

    /**
     * Checa se tem um warn com tal nome
     *
     * @param n
     * @return
     */
    public boolean hasByName(String n) {
        return getByName(n) != null;
    }

}
