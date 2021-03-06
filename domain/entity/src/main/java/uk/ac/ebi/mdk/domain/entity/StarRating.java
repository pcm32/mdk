/*
 * Copyright (C) 2012  John May and Pablo Moreno
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package uk.ac.ebi.mdk.domain.entity;


/**
 *          Rating - 2011.12.09 <br>
 *          Class defines a rating for an entity
 * @version $Rev$ : Last Changed $Date$
 * @author  johnmay
 * @author  $Author$ (this version)
 */
public enum StarRating implements Rating {

    ONE_STAR((byte) 1),
    TWO_STARS((byte) 2),
    THREE_STARS((byte) 3),
    FOUR_STARS((byte) 4),
    FIVE_STARS((byte) 5),
    NO_RATING((byte) 0);

    private byte score;


    private StarRating(byte score) {
        this.score = score;
    }


    public byte getScore() {
        return score;
    }
    
    
    
    public  static Rating getRating(byte score){
        for(Rating rating : StarRating.values()){
            if(rating.getScore() == score){
                return rating;
            }
        }
        return NO_RATING;
    }
    
}
