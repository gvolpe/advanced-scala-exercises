package com.gvolpe.advanced

import com.gvolpe.advanced.monoids.SuperAdderApp.Order

import scalaz.Monoid

package object monoids {

  object OrderOps {
    implicit val orderMonoid: Monoid[Order] = Monoid.instance[Order]((o1, o2) =>
      Order(o1.totalCost + o2.totalCost, o1.quantity + o2.quantity), Order(0, 0))
  }

}
